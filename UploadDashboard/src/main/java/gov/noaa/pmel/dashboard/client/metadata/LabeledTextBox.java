package gov.noaa.pmel.dashboard.client.metadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import gov.noaa.pmel.dashboard.client.UploadDashboard;

public class LabeledTextBox extends Composite implements HasText, HasValue<String> {

    interface LabeledTextBoxUiBinder extends UiBinder<FlowPanel,LabeledTextBox> {
    }

    private static final LabeledTextBoxUiBinder labeledTextBoxUiBinder = GWT.create(LabeledTextBoxUiBinder.class);

    @UiField
    HTML prefixHtml;
    @UiField
    TextBox valueBox;
    @UiField
    Label suffixLabel;

    private final SafeHtml validValueHtml;
    private final SafeHtml invalidValueHtml;
    private boolean valid;

    /**
     * Creates a TextBox with an HTML before and a Label after it.
     * Text in the labels are aligned so as to be closest to the TextBox.
     *
     * @param prefix
     *         safe HTML (in "acceptable value" format) to appear in the HTML before the TextBox; cannot be null
     * @param prefixWidth
     *         width, in CSS units, of the HTML before the TextBox; see: {@link HTML#setWidth(String)}.
     *         If null, no specification of the width is made.
     * @param valueWidth
     *         width, in CSS units, of the TextBox; see: {@link TextBox#setWidth(String)}.
     *         If null, no specification of the width is made.
     * @param suffix
     *         text to appear in the label after the TextBox; if null, this label will be hidden
     * @param suffixWidth
     *         width, in CSS units, of the label after the TextBox; see: {@link Label#setWidth(String)}.
     *         If null, or if suffix is null, no specification of the width is made.
     */
    public LabeledTextBox(String prefix, String prefixWidth, String valueWidth, String suffix, String suffixWidth) {
        initWidget(labeledTextBoxUiBinder.createAndBindUi(this));

        validValueHtml = SafeHtmlUtils.fromString(prefix);
        invalidValueHtml = UploadDashboard.invalidLabelHtml(validValueHtml);

        prefixHtml.setHTML(validValueHtml);
        valid = true;
        if ( prefixWidth != null )
            prefixHtml.setWidth(prefixWidth);
        if ( valueWidth != null )
            valueBox.setWidth(valueWidth);
        if ( suffix != null ) {
            suffixLabel.setText(suffix);
            if ( suffixWidth != null )
                suffixLabel.setWidth(suffixWidth);
        }
        else
            suffixLabel.setVisible(false);
    }

    @Override
    public String getText() {
        return valueBox.getText();
    }

    @Override
    public void setText(String text) {
        valueBox.setText(text);
    }

    @Override
    public String getValue() {
        return valueBox.getValue();
    }

    @Override
    public void setValue(String value) {
        valueBox.setValue(value);
    }

    @Override
    public void setValue(String value, boolean fireEvents) {
        valueBox.setValue(value, fireEvents);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return valueBox.addValueChangeHandler(handler);
    }

    /**
     * @param suffix
     *         displays the new value for the suffix label.  If null, the label is hidden.
     */
    public void setSuffix(String suffix) {
        if ( suffix != null ) {
            suffixLabel.setText(suffix);
            suffixLabel.setVisible(true);
        }
        else {
            suffixLabel.setVisible(false);
        }
    }

    /**
     * If isInvalid is true, adds highlighting of the prefix text to indicate the TextBox value is invalid.
     * If isInvalid is false, removes the highlighting of the prefix text (resets it to normal),
     * to indicate the TextBox value is valid.
     *
     * @param isInvalid
     *         is the value invalid (true) or valid (false)
     */
    public void markInvalid(boolean isInvalid) {
        if ( isInvalid ) {
            if ( valid ) {
                prefixHtml.setHTML(invalidValueHtml);
                valid = false;
            }
        }
        else {
            if ( !valid ) {
                prefixHtml.setHTML(validValueHtml);
                valid = true;
            }

        }
    }

}
