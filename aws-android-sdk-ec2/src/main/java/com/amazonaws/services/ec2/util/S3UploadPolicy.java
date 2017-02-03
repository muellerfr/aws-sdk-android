/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.services.ec2.util;

import static com.amazonaws.util.StringUtils.UTF8;

import com.amazonaws.util.Base64;
import com.amazonaws.util.DateUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class represents S3 upload policy. Policy string representation and
 * signature to be used within EC2 bundling API.
 */
public class S3UploadPolicy {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private String policySignature;
    private String policyString;

    /**
     * Creates a new S3 upload policy object from the specified parameters. Once
     * constructed, callers can access the policy string and policy signature to
     * use with the EC2 bundling API.
     *
     * @param awsAccessKeyId The AWS access key ID for the S3 bucket the
     *            bundling artifacts should be stored in.
     * @param awsSecretKey The AWS secret key for the specified access key.
     * @param bucketName The name of the bucket to store the bundling artifacts
     *            in.
     * @param prefix The prefix for the bundling artifacts.
     * @param expireInMinutes The number of minutes before the upload policy
     *            expires and is unable to be used.
     */
    public S3UploadPolicy(String awsAccessKeyId,
            String awsSecretKey,
            String bucketName,
            String prefix,
            int expireInMinutes) {
        Date expirationDate = new Date(System.currentTimeMillis() + 60L * 1000 * expireInMinutes);
        StringBuilder builder = new StringBuilder();
        builder.append("{")
                .append("\"expiration\": \"")
                .append(DateUtils.format(DateUtils.ALTERNATE_ISO8601_DATE_PATTERN, expirationDate))
                .append("\",")
                .append("\"conditions\": [")
                .append("{\"bucket\": \"")
                .append(bucketName)
                .append("\"},")
                .append("{\"acl\": \"")
                .append("ec2-bundle-read")
                .append("\"},")
                .append("[\"starts-with\", \"$key\", \"")
                .append(prefix)
                .append("\"]")
                .append("]}");
        try {
            this.policyString = base64Encode(builder.toString().getBytes(UTF8));
            this.policySignature = signPolicy(awsSecretKey, policyString);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to generate S3 upload policy", ex);
        }
    }

    /**
     * Base64 representation of the serialized policy. Use policy generated by
     * this method for passing to EC2 bundling calls.
     *
     * @return Base64 policy
     */
    public String getPolicyString() {
        return this.policyString;
    }

    /**
     * Policy signature in base64 format Use signature generated by this method
     * for passing to EC2 bunding calls along with policy.
     *
     * @return Base64 signature
     */
    public String getPolicySignature() {
        return this.policySignature;
    }

    private String signPolicy(String awsSecretKey, String base64EncodedPolicy) throws
            NoSuchAlgorithmException,
            InvalidKeyException,
            UnsupportedEncodingException {
        SecretKeySpec signingKey = new SecretKeySpec(awsSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return base64Encode(mac.doFinal(base64EncodedPolicy.getBytes()));
    }

    private String base64Encode(byte[] data) {
        return Base64.encodeAsString(data).replaceAll("\\s", "");
    }

}
