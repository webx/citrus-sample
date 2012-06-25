/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.sample.petstore.dal.dataobject;

import static java.util.Calendar.*;

import java.util.Calendar;
import java.util.Date;

public class Account {
    private final User   user;
    private       String email;
    private       String firstName;
    private       String lastName;
    private       String status;
    private       String address1;
    private       String address2;
    private       String city;
    private       String state;
    private       String zip;
    private       String country;
    private       String phone;
    private       String creditCardNumber;
    private       String creditCardType;
    private       Date   creditCardExpiry;

    public Account(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Date getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(Date creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public int getCreditCardExpiryMonth() {
        return getCreditCardExpiryField(MONTH);
    }

    public void setCreditCardExpiryMonth(int month) {
        setCreditCardExpiryField(MONTH, month);
    }

    public int getCreditCardExpiryYear() {
        return getCreditCardExpiryField(YEAR);
    }

    public void setCreditCardExpiryYear(int year) {
        setCreditCardExpiryField(YEAR, year);
    }

    private int getCreditCardExpiryField(int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        if (creditCardExpiry != null) {
            calendar.setTime(creditCardExpiry);
        }

        switch (field) {
            case YEAR:
                return calendar.get(YEAR);

            case MONTH:
                return calendar.get(MONTH) + 1;

            default:
                throw new UnsupportedOperationException("unknown field #" + field);
        }
    }

    private void setCreditCardExpiryField(int field, int value) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        if (creditCardExpiry != null) {
            calendar.setTime(creditCardExpiry);
        }

        int year = calendar.get(YEAR);
        int month = calendar.get(MONTH) + 1;

        switch (field) {
            case YEAR:
                year = value;
                break;

            case MONTH:
                month = value;
                break;

            default:
                throw new UnsupportedOperationException("unknown field #" + field);
        }

        calendar.set(YEAR, year);
        calendar.set(MONTH, month - 1);

        creditCardExpiry = calendar.getTime();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }
}
