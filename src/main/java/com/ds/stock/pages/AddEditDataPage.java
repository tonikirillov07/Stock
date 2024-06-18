package com.ds.stock.pages;

import com.ds.stock.Constants;
import com.ds.stock.additionalNodes.*;
import com.ds.stock.data.*;
import com.ds.stock.data.dataUtils.writer.DataWriter;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.*;
import com.ds.stock.utils.Utils;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.enums.InputTypes;
import com.ds.stock.utils.eventListeners.IOnAction;
import com.ds.stock.utils.settings.SettingsManager;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static com.ds.stock.data.InvoiceData.findInvoiceDataById;
import static com.ds.stock.utils.Utils.checkPhoneNumber;
import static com.ds.stock.utils.Utils.defaultCategoryMenuItemsAction;

public class AddEditDataPage extends Page{
    private final boolean isEditMode;
    private VBox scrollViewContent;

    public AddEditDataPage(Page prevoiusPage, VBox contentVbox, String title, boolean isEditMode) {
        super(prevoiusPage, contentVbox, title);
        this.isEditMode = isEditMode;
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();

        if(!isEditMode)
            createCategoryMenuButton();

        createScrollPane();
        createBackButton();
    }

    private void createScrollPane() {
        AdditionalScrollPane additionalScrollPane = new AdditionalScrollPane();
        additionalScrollPane.createScrollPane();

        scrollViewContent = additionalScrollPane.getScrollPaneContentVbox();
        scrollViewContent.setPadding(new Insets(0d, 50d, 0d, 50d));
        addNodeToTile(additionalScrollPane.getScrollPane());
    }

    private void createBackButton() {
        BackButton.createBackButton(this, scrollViewContent);
    }

    private void createDeleteButton(long id, String tableName){
        try {
            AdditionalButton additionalButton = new AdditionalButton("Удалить", 200d, 40d, "#AB0000", Color.WHITE);
            VBox.setMargin(additionalButton, new Insets(15d));
            additionalButton.setOnAction(actionEvent -> {
                DatabaseService.deleteRecord(id, tableName, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                goToPreviousPage();
            });

            scrollViewContent.getChildren().add(additionalButton);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void createCategoryMenuButton() {
        try {
            CategoryMenuButton categoryMenuButton = new CategoryMenuButton();
            VBox.setMargin(categoryMenuButton, new Insets(10d));

            categoryMenuButton.getGoodsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getGoodsMenuButton(), categoryMenuButton);
                createGoodsComponents(null);
            });
            categoryMenuButton.getLocationsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getLocationsMenuButton(), categoryMenuButton);
                createLocationsComponents(null);
            });
            categoryMenuButton.getCustomersMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getCustomersMenuButton(), categoryMenuButton);
                createCustomerComponents(null);
            });
            categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton(), categoryMenuButton);
                createInvoicesComponents(null);
            });
            categoryMenuButton.getProvidersMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getProvidersMenuButton(), categoryMenuButton);
                createProviderComponents(null);
            });
            categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton(), categoryMenuButton);
                createAppliedInvoicesForPurchasedGoodsComponents(null);
            });

            addNodeToTile(categoryMenuButton);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createAppliedInvoicesForPurchasedGoodsComponents(AppliedInvoiceForPurchaseGoodData appliedInvoiceForPurchaseGoodData) {
        try{
            scrollViewContent.getChildren().clear();

            AdditionalTextField providerIdTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID поставщика", Utils.getImage("images/id.png"), false);
            providerIdTextField.setInputType(InputTypes.NUMERIC);

            DateSelector dateSelector = new DateSelector();
            AdditionalTextField goodsIdsTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID купленных товаров (Например: 1,2,3,..)", Utils.getImage("images/goods.png"), false);
            AdditionalTextField goodsCountTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Количество товаров", Utils.getImage("images/count.png"), false);
            goodsCountTextField.setText("0");

            goodsIdsTextField.addOnTextTyping(currentText -> goodsCountTextField.setText(String.valueOf(currentText.split(",").length)));

            if(isEditMode){
                providerIdTextField.setText(String.valueOf(appliedInvoiceForPurchaseGoodData.getProviderId()));
                dateSelector.getDatePicker().setValue(Utils.convertStringToLocalDate(appliedInvoiceForPurchaseGoodData.getDate()));
                goodsIdsTextField.setText(Utils.convertGoodsListToString(appliedInvoiceForPurchaseGoodData.getGoods()));
                goodsCountTextField.setText(String.valueOf(appliedInvoiceForPurchaseGoodData.getGoodsCount()));
            }

            scrollViewContent.getChildren().addAll(providerIdTextField, dateSelector, goodsIdsTextField, goodsCountTextField);
            createNextButton(() -> {
                try {
                    if (Utils.checkFields(providerIdTextField, goodsIdsTextField, goodsCountTextField))
                        return;

                    if(dateSelector.getDatePicker().getValue() == null){
                        ErrorDialog.show(new NullPointerException("Выберете дату"));
                        return;
                    }

                    long enteredProviderId = Long.parseLong(providerIdTextField.getText());

                    if(ProviderData.findProviderById(enteredProviderId) == null){
                        providerIdTextField.setError();
                        ErrorDialog.show(new IllegalArgumentException("Поставщик с ID " + providerIdTextField.getText() + " не найден"));
                        return;
                    }

                    String[] enteredIds = goodsIdsTextField.getText().replace(" ", "").split(",");
                    boolean allIdsIsValid = true;

                    for (String enteredId : enteredIds) {
                        if(GoodData.findGoodById(Long.parseLong(enteredId)) == null){
                            ErrorDialog.show(new IllegalArgumentException("Товара с id " + enteredId + " не существует"));
                            allIdsIsValid = false;
                        }
                    }

                    if(!allIdsIsValid) {
                        goodsIdsTextField.setError();
                        return;
                    }

                    if(!isEditMode)
                        DataWriter.addAppliedInvoicesForPurchaseGoods(new AppliedInvoiceForPurchaseGoodData(dateSelector.getDatePicker().getValue().toString(), enteredProviderId, Utils.convertStringToGoodDataList(goodsIdsTextField.getText())));
                    else {
                        DatabaseService.changeValue(AppliedInvoicesForPurchaseGoods.PROVIDER_ID_ROW, providerIdTextField.getText(), appliedInvoiceForPurchaseGoodData.getId(), AppliedInvoicesForPurchaseGoods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(AppliedInvoicesForPurchaseGoods.DATE_ROW, dateSelector.getDatePicker().getValue().toString(), appliedInvoiceForPurchaseGoodData.getId(), AppliedInvoicesForPurchaseGoods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(AppliedInvoicesForPurchaseGoods.GOODS_IDS_ROW, goodsIdsTextField.getText(), appliedInvoiceForPurchaseGoodData.getId(), AppliedInvoicesForPurchaseGoods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(AppliedInvoicesForPurchaseGoods.GOODS_COUNT_ROW, goodsCountTextField.getText(), appliedInvoiceForPurchaseGoodData.getId(), AppliedInvoicesForPurchaseGoods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }
                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(appliedInvoiceForPurchaseGoodData.getId(), AppliedInvoicesForPurchaseGoods.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createProviderComponents(ProviderData providerData) {
        try{
            scrollViewContent.getChildren().clear();

            DefaultTextFields defaultTextFields = new DefaultTextFields();
            defaultTextFields.createTextFields("Имя поставщика", "images/provider.png");

            AdditionalTextField appliedInvoiceField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID приходной накладной", Utils.getImage("images/invoice.png"), false);
            scrollViewContent.getChildren().addAll(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress(), appliedInvoiceField);

            if(isEditMode){
                defaultTextFields.getAdditionalTextFieldName().setText(providerData.getName());
                defaultTextFields.getAdditionalTextFieldTelephone().setText(providerData.getTelephone());
                defaultTextFields.getAdditionalTextFieldAddress().setText(providerData.getAddress());
                appliedInvoiceField.setText(String.valueOf(providerData.getAppliedInvoiceForPurchaseGood()));
            }

            createNextButton(() -> {
                try {
                    if (Utils.checkFields(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress(), appliedInvoiceField))
                        return;

                    if (!Utils.checkPhoneNumber(defaultTextFields.getAdditionalTextFieldTelephone().getText()))
                        return;

                    long appliedInvoiceForPurchaseGoodDataId = Long.parseLong(appliedInvoiceField.getText());

                    if (AppliedInvoiceForPurchaseGoodData.findAppliedInvoiceForPurchaseGoodDataById(appliedInvoiceForPurchaseGoodDataId) == null) {
                        ErrorDialog.show(new IllegalArgumentException("Прикладная накладная с id " + appliedInvoiceForPurchaseGoodDataId + " не найдена"));
                        return;
                    }

                    if(!isEditMode)
                        DataWriter.addProvider(new ProviderData(defaultTextFields.getAdditionalTextFieldName().getText(), defaultTextFields.getAdditionalTextFieldAddress().getText(), defaultTextFields.getAdditionalTextFieldTelephone().getText(),
                                appliedInvoiceForPurchaseGoodDataId));
                    else{
                        DatabaseService.changeValue(Providers.NAME_ROW, defaultTextFields.getAdditionalTextFieldName().getText(), providerData.getId(), Providers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Providers.TELEPHONE_ROW, defaultTextFields.getAdditionalTextFieldTelephone().getText(), providerData.getId(), Providers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Providers.ADDRESS_ROW, defaultTextFields.getAdditionalTextFieldAddress().getText(), providerData.getId(), Providers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Providers.APPLIED_INVOICE_FOR_PURCHASED_GOOD_ID_ROW, appliedInvoiceField.getText(), providerData.getId(), Providers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }
                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(providerData.getId(), Providers.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createInvoicesComponents(InvoiceData invoiceData){
        try {
            scrollViewContent.getChildren().clear();

            AdditionalTextField customerIdTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID покупателя", Utils.getImage("images/id.png"), false);
            customerIdTextField.setInputType(InputTypes.NUMERIC);

            DateSelector dateSelector = new DateSelector();
            AdditionalTextField goodsIdsTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID проданных товаров (Например: 1,2,3,..)", Utils.getImage("images/goods.png"), false);
            AdditionalTextField goodsCountTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Количество товаров", Utils.getImage("images/count.png"), false);
            goodsCountTextField.setText("0");

            goodsIdsTextField.addOnTextTyping(currentText -> goodsCountTextField.setText(String.valueOf(currentText.split(",").length)));

            if(isEditMode){
                customerIdTextField.setText(String.valueOf(invoiceData.getCustomerDataId()));
                dateSelector.getDatePicker().setValue(Utils.convertStringToLocalDate(invoiceData.getDate()));
                goodsIdsTextField.setText(invoiceData.getGoods());
                goodsCountTextField.setText(String.valueOf(invoiceData.getGoodsCount()));
            }

            scrollViewContent.getChildren().addAll(customerIdTextField, dateSelector, goodsIdsTextField, goodsCountTextField);
            createNextButton(() -> {
                try {
                    if (Utils.checkFields(customerIdTextField, goodsIdsTextField, goodsCountTextField))
                        return;

                    if(dateSelector.getDatePicker().getValue() == null){
                        ErrorDialog.show(new NullPointerException("Выберете дату"));
                        return;
                    }

                    if(CustomerData.findCustomerById(Long.parseLong(customerIdTextField.getText())) == null){
                        customerIdTextField.setError();
                        ErrorDialog.show(new IllegalArgumentException("Покупатель с ID " + customerIdTextField.getText() + " не найден"));
                        return;
                    }

                    String[] enteredIds = goodsIdsTextField.getText().replace(" ", "").split(",");
                    boolean allIdsIsValid = true;

                    for (String enteredId : enteredIds) {
                        if(GoodData.findGoodById(Long.parseLong(enteredId)) == null){
                            ErrorDialog.show(new IllegalArgumentException("Товара с id " + enteredId + " не существует"));
                            allIdsIsValid = false;
                        }
                    }

                    if(!allIdsIsValid) {
                        goodsIdsTextField.setError();
                        return;
                    }

                    if(!isEditMode)
                        DataWriter.addInvoice(new InvoiceData(Long.parseLong(customerIdTextField.getText()), dateSelector.getDatePicker().getValue().toString(), goodsIdsTextField.getText()));
                    else {
                        DatabaseService.changeValue(Invoices.CUSTOMER_ID_ROW, customerIdTextField.getText(), invoiceData.getId(), Invoices.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Invoices.DATE_ROW, dateSelector.getDatePicker().getValue().toString(), invoiceData.getId(), Invoices.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Invoices.GOODS_IDS_ROW, goodsIdsTextField.getText(), invoiceData.getId(), Invoices.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Invoices.GOODS_COUNT_ROW, goodsCountTextField.getText(), invoiceData.getId(), Invoices.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }
                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(invoiceData.getId(), Invoices.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createCustomerComponents(CustomerData customerData) {
        try{
            scrollViewContent.getChildren().clear();

            DefaultTextFields defaultTextFields = new DefaultTextFields();
            defaultTextFields.createTextFields("Имя покупателя", "images/user.png");

            AdditionalTextField invoiceIdTextField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ID счета-фактуры", Utils.getImage("images/id.png"), false);
            invoiceIdTextField.setInputType(InputTypes.NUMERIC);

            if(isEditMode){
                defaultTextFields.getAdditionalTextFieldName().setText(customerData.getName());
                defaultTextFields.getAdditionalTextFieldAddress().setText(customerData.getAddress());
                defaultTextFields.getAdditionalTextFieldTelephone().setText(customerData.getTelephone());
                invoiceIdTextField.setText(String.valueOf(customerData.getInvoiceDataId()));
            }

            scrollViewContent.getChildren().addAll(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress(), invoiceIdTextField);
            createNextButton(() -> {
                try {
                    if (Utils.checkFields(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress(), invoiceIdTextField))
                        return;

                    if(CustomerData.findCustomerByName(defaultTextFields.getAdditionalTextFieldName().getText()) & !isEditMode){
                        ErrorDialog.show(new IllegalArgumentException("Такой покупатель уже есть"));
                        return;
                    }

                    if (!checkPhoneNumber(defaultTextFields.getAdditionalTextFieldTelephone().getText()))
                        return;

                    long invoiceId = Long.parseLong(invoiceIdTextField.getText());

                    if (findInvoiceDataById(invoiceId) == null) {
                        ErrorDialog.show(new IllegalArgumentException("Счет-фактуры с id " + invoiceId + " не найден"));
                        return;
                    }

                    if(!isEditMode)
                        DataWriter.addCustomer(new CustomerData(defaultTextFields.getAdditionalTextFieldName().getText(), defaultTextFields.getAdditionalTextFieldAddress().getText(),
                                defaultTextFields.getAdditionalTextFieldTelephone().getText(), invoiceId));
                    else {
                        DatabaseService.changeValue(Customers.NAME_ROW, defaultTextFields.getAdditionalTextFieldName().getText(), customerData.getId(), Customers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Customers.TELEPHONE_ROW, defaultTextFields.getAdditionalTextFieldTelephone().getText(), customerData.getId(), Customers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Customers.ADDRESS_ROW, defaultTextFields.getAdditionalTextFieldAddress().getText(), customerData.getId(), Customers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Customers.INVOICE_ID_ROW, invoiceIdTextField.getText(), customerData.getId(), Customers.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }
                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(customerData.getId(), Customers.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createLocationsComponents(LocationData locationData) {
        try {
            scrollViewContent.getChildren().clear();

            DefaultTextFields defaultTextFields = new DefaultTextFields();
            defaultTextFields.createTextFields("Название", "images/address.png");

            if(isEditMode){
                defaultTextFields.getAdditionalTextFieldName().setText(locationData.getName());
                defaultTextFields.getAdditionalTextFieldAddress().setText(locationData.getAddress());
                defaultTextFields.getAdditionalTextFieldTelephone().setText(locationData.getTelephone());
            }

            scrollViewContent.getChildren().addAll(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress());
            createNextButton(() -> {
                try {
                    if (Utils.checkFields(defaultTextFields.getAdditionalTextFieldName(), defaultTextFields.getAdditionalTextFieldTelephone(), defaultTextFields.getAdditionalTextFieldAddress()))
                        return;

                    if (!Utils.checkPhoneNumber(defaultTextFields.getAdditionalTextFieldTelephone().getText()))
                        return;

                    if(!isEditMode)
                        DataWriter.addLocation(new LocationData(defaultTextFields.getAdditionalTextFieldName().getText(), defaultTextFields.getAdditionalTextFieldAddress().getText(), defaultTextFields.getAdditionalTextFieldTelephone().getText()));
                    else{
                        DatabaseService.changeValue(Locations.NAME_ROW, defaultTextFields.getAdditionalTextFieldName().getText(), locationData.getId(), Locations.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Locations.TELEPHONE_ROW, defaultTextFields.getAdditionalTextFieldTelephone().getText(), locationData.getId(), Locations.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Locations.ADDRESS_ROW, defaultTextFields.getAdditionalTextFieldAddress().getText(), locationData.getId(), Locations.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }

                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(locationData.getId(), Locations.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void createGoodsComponents(GoodData goodData) {
        try {
            scrollViewContent.getChildren().clear();

            AdditionalTextField nameField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Название товара", Utils.getImage("images/all_symbols.png"), false);
            AdditionalTextField unitField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Единица измерения", Utils.getImage("images/unit.png"), false);
            AdditionalTextField purchaseCostField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Цена покупки", Utils.getImage("images/cost.png"), false);
            AdditionalTextField saleCostField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Цена продажи", Utils.getImage("images/cost.png"), false);

            if(isEditMode){
                nameField.setText(goodData.getName());
                unitField.setText(goodData.getUnit());
                purchaseCostField.setText(String.valueOf(goodData.getPurchaseCost()));
                saleCostField.setText(String.valueOf(goodData.getSaleCost()));
            }

            scrollViewContent.getChildren().addAll(nameField, unitField, purchaseCostField, saleCostField);
            createNextButton(() -> {
                try {
                    if (Utils.checkFields(nameField, unitField, purchaseCostField, saleCostField))
                        return;

                    double purchaseCost = Double.parseDouble(purchaseCostField.getText());
                    double saleCost = Double.parseDouble(saleCostField.getText());

                    if(!isEditMode)
                        DataWriter.addGood(new GoodData(nameField.getText(), purchaseCost, saleCost, unitField.getText()));
                    else{
                        DatabaseService.changeValue(Goods.NAME_ROW, nameField.getText(), goodData.getId(), Goods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Goods.UNIT_ROW, unitField.getText(), goodData.getId(), Goods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Goods.PURCHASE_COST_ROW, purchaseCostField.getText(), goodData.getId(), Goods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                        DatabaseService.changeValue(Goods.SALE_COST_ROW, saleCostField.getText(), goodData.getId(), Goods.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    }
                    goToPreviousPage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });

            if(isEditMode)
                createDeleteButton(goodData.getId(), Goods.TABLE_NAME);

            createBackButton();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void createNextButton(IOnAction onAction){
        try {
            AdditionalButton additionalButton = new AdditionalButton("Далее", 200d, 40d, "#3700AB", Color.WHITE);
            VBox.setMargin(additionalButton, new Insets(15d));
            additionalButton.setOnAction(actionEvent -> onAction.onAction());

            scrollViewContent.getChildren().add(additionalButton);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
