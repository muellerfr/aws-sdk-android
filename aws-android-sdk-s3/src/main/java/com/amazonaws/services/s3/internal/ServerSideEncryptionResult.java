/*
 * Copyright 2011-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.s3.internal;

import com.amazonaws.services.s3.Headers;

/**
 * Interface for service responses that include the server-side-encryption
 * related headers.
 *
 * @see Headers#SERVER_SIDE_ENCRYPTION
 * @see Headers#SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM
 * @see Headers#SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5
 * @see Headers#SERVER_SIDE_ENCRYPTION_KMS_KEY_ID
 */
public interface ServerSideEncryptionResult {

    /**
     * Returns the server-side encryption algorithm if the object is encrypted
     * using AWS-managed keys. Otherwise returns null.
     */
    public String getSSEAlgorithm();

    /**
     * Sets the server-side encryption algorithm for the response.
     *
     * @param algorithm The server-side encryption algorithm for the response.
     */
    public void setSSEAlgorithm(String algorithm);

    /**
     * Sets the KMS Key to use when encrypting objects server-side using KMS. In
     * order to use this field you must also call
     * setSSEAlgorithm(ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION)
     * 
     * @param kmsKeyId The Id of the KMS key to encrypt the data with.
     */
    public void setSSEKMSKeyId(String kmsKeyId);

    /**
     * Returns the KMS Key Id used for server-side encryption if set, or null
     * otherwise.
     */
    public String getSSEKMSKeyId();

    /**
     * Returns the server-side encryption algorithm if the object is encrypted
     * using customer-provided keys. Otherwise returns null.
     */
    public String getSSECustomerAlgorithm();

    /**
     * Sets the server-side encryption algorithm used when encrypting the object
     * with customer-provided keys.
     *
     * @param algorithm The server-side encryption algorithm used when
     *            encrypting the object with customer-provided keys.
     */
    public void setSSECustomerAlgorithm(String algorithm);

    /**
     * Returns the base64-encoded MD5 digest of the encryption key for
     * server-side encryption, if the object is encrypted using
     * customer-provided keys. Otherwise returns null.
     */
    public String getSSECustomerKeyMd5();

    /**
     * Sets the base64-encoded MD5 digest of the encryption key for server-side
     * encryption.
     *
     * @param md5Digest The base64-encoded MD5 digest of the encryption key for
     *            server-side encryption.
     */
    public void setSSECustomerKeyMd5(String md5Digest);
}
