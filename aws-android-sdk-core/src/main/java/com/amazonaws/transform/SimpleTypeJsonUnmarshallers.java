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

package com.amazonaws.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.util.Base64;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class SimpleTypeJsonUnmarshallers {
    /**
     * Unmarshaller for String values.
     */
    public static class StringJsonUnmarshaller implements
            Unmarshaller<String, JsonUnmarshallerContext> {
        @Override
        public String unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            return unmarshallerContext.getReader().nextString();
        }

        private static StringJsonUnmarshaller instance;

        public static StringJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new StringJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Double values.
     */
    public static class DoubleJsonUnmarshaller implements
            Unmarshaller<Double, JsonUnmarshallerContext> {
        @Override
        public Double unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String doubleString = unmarshallerContext.getReader().nextString();
            return (doubleString == null) ? null : Double.parseDouble(doubleString);
        }

        private static DoubleJsonUnmarshaller instance;

        public static DoubleJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new DoubleJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Integer values.
     */
    public static class IntegerJsonUnmarshaller implements
            Unmarshaller<Integer, JsonUnmarshallerContext> {
        @Override
        public Integer unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String intString = unmarshallerContext.getReader().nextString();
            return (intString == null) ? null : Integer.parseInt(intString);
        }

        private static IntegerJsonUnmarshaller instance;

        public static IntegerJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new IntegerJsonUnmarshaller();
            return instance;
        }
    }

    public static class BigIntegerJsonUnmarshaller implements
            Unmarshaller<BigInteger, JsonUnmarshallerContext> {
        @Override
        public BigInteger unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String intString = unmarshallerContext.getReader().nextString();
            return (intString == null) ? null : new BigInteger(intString);
        }

        private static BigIntegerJsonUnmarshaller instance;

        public static BigIntegerJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new BigIntegerJsonUnmarshaller();
            return instance;
        }
    }

    public static class BigDecimalJsonUnmarshaller implements
            Unmarshaller<BigDecimal, JsonUnmarshallerContext> {
        @Override
        public BigDecimal unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String s = unmarshallerContext.getReader().nextString();
            return (s == null) ? null : new BigDecimal(s);
        }

        private static BigDecimalJsonUnmarshaller instance;

        public static BigDecimalJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new BigDecimalJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Boolean values.
     */
    public static class BooleanJsonUnmarshaller implements
            Unmarshaller<Boolean, JsonUnmarshallerContext> {
        @Override
        public Boolean unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String booleanString = unmarshallerContext.getReader().nextString();
            return (booleanString == null) ? null : Boolean.parseBoolean(booleanString);
        }

        private static BooleanJsonUnmarshaller instance;

        public static BooleanJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new BooleanJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Float values.
     */
    public static class FloatJsonUnmarshaller implements
            Unmarshaller<Float, JsonUnmarshallerContext> {
        @Override
        public Float unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String floatString = unmarshallerContext.getReader().nextString();
            return (floatString == null) ? null : Float.valueOf(floatString);
        }

        private static FloatJsonUnmarshaller instance;

        public static FloatJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new FloatJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Long values.
     */
    public static class LongJsonUnmarshaller implements Unmarshaller<Long, JsonUnmarshallerContext> {
        @Override
        public Long unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String longString = unmarshallerContext.getReader().nextString();
            return (longString == null) ? null : Long.parseLong(longString);
        }

        private static LongJsonUnmarshaller instance;

        public static LongJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new LongJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Byte values.
     */
    public static class ByteJsonUnmarshaller implements Unmarshaller<Byte, JsonUnmarshallerContext> {
        @Override
        public Byte unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String byteString = unmarshallerContext.getReader().nextString();
            return (byteString == null) ? null : Byte.valueOf(byteString);
        }

        private static ByteJsonUnmarshaller instance;

        public static ByteJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new ByteJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for Date values - JSON dates come in as epoch seconds.
     */
    public static class DateJsonUnmarshaller implements Unmarshaller<Date, JsonUnmarshallerContext> {
        @Override
        public Date unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String dateString = unmarshallerContext.getReader().nextString();
            if (dateString == null)
                return null;

            try {
                Number number = NumberFormat.getInstance(new Locale("en")).parse(dateString);
                return new Date(number.longValue() * 1000);
            } catch (ParseException e) {
                String errorMessage = "Unable to parse date '" + dateString + "':  "
                        + e.getMessage();
                throw new AmazonClientException(errorMessage, e);
            }
        }

        private static DateJsonUnmarshaller instance;

        public static DateJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new DateJsonUnmarshaller();
            return instance;
        }
    }

    /**
     * Unmarshaller for ByteBuffer values.
     */
    public static class ByteBufferJsonUnmarshaller implements
            Unmarshaller<ByteBuffer, JsonUnmarshallerContext> {
        @Override
        public ByteBuffer unmarshall(JsonUnmarshallerContext unmarshallerContext) throws Exception {
            String base64EncodedString = unmarshallerContext.getReader().nextString();
            byte[] decodedBytes = Base64.decode(base64EncodedString);
            return ByteBuffer.wrap(decodedBytes);

        }

        private static ByteBufferJsonUnmarshaller instance;

        public static ByteBufferJsonUnmarshaller getInstance() {
            if (instance == null)
                instance = new ByteBufferJsonUnmarshaller();
            return instance;
        }
    }

}
