package com.ds.stock.additionalNodes;

import com.ds.stock.utils.Utils;

public class DefaultTextFields {
    private AdditionalTextField additionalTextFieldName, additionalTextFieldAddress, additionalTextFieldTelephone;

    public void createTextFields(String nameText, String nameFieldIconPath) {
        additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, nameText,
                Utils.getImage(nameFieldIconPath), false);

        additionalTextFieldAddress = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Адрес",
                Utils.getImage("images/address.png"), false);

        additionalTextFieldTelephone = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Телефон",
                Utils.getImage("images/telephone.png"), false);

    }

    public AdditionalTextField getAdditionalTextFieldName() {
        return additionalTextFieldName;
    }

    public AdditionalTextField getAdditionalTextFieldAddress() {
        return additionalTextFieldAddress;
    }

    public AdditionalTextField getAdditionalTextFieldTelephone() {
        return additionalTextFieldTelephone;
    }
}
