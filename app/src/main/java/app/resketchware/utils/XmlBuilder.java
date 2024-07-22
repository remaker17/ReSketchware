package app.resketchware.utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class XmlBuilder {
  private static final Pattern WIDGET_NAME_PATTERN = Pattern.compile("\\w*\\..*\\.");

  private final ArrayList<AttributeBuilder> attributes = new ArrayList<>();
  private final ArrayList<XmlBuilder> childElements = new ArrayList<>();

  private final String tagName;
  private String textContent;
  private String lineBreak;

  private int indentationLevel = 0;
  private final boolean isSelfClosing;

  public XmlBuilder(String tagName) {
    this(tagName, false);
  }

  public XmlBuilder(String tagName, boolean isSelfClosing) {
    this.tagName = tagName;
    this.isSelfClosing = isSelfClosing;
  }

  private String getZeroIndentation() {
    return getIndentation(0);
  }

  private String getIndentation(int indentationSize) {
    StringBuilder indentation = new StringBuilder();
    for (int i = 0; i < indentationLevel + indentationSize; i++) {
      indentation.append("\t");
    }
    return indentation.toString();
  }

  public void addNamespaceDeclaration(int position, String namespace, String attribute, String value) {
    attributes.add(position, new AttributeBuilder(namespace, attribute, value));
  }

  public void addChildElement(XmlBuilder childElement) {
    childElement.indentationLevel = indentationLevel + 1;
    childElements.add(childElement);
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public void addAttribute(String namespace, String attribute, String value) {
    attributes.add(new AttributeBuilder(namespace, attribute, value));
  }

  public void addAttributeValue(String value) {
    attributes.add(new AttributeBuilder(value));
  }

  public String toCode() {
    StringBuilder resultCode = new StringBuilder();
    resultCode.append(getZeroIndentation());
    resultCode.append("<");
    resultCode.append(tagName);
    for (AttributeBuilder attribute : attributes) {
      if (attributes.size() <= 1 || isSelfClosing) {
        resultCode.append(" ");
      } else {
        resultCode.append("\r\n");
        resultCode.append(getIndentation(1));
        lineBreak = "\r\n" + getIndentation(1);
      }
      resultCode.append(attribute.toCode());
    }
    if (childElements.isEmpty()) {
      if (textContent == null || textContent.isEmpty()) {
        resultCode.append(" />");
      } else {
        resultCode.append(">");
        resultCode.append(textContent);
        resultCode.append("</");
        resultCode.append(tagName);
        resultCode.append(">");
      }
    } else {
      resultCode.append(">");
      resultCode.append("\r\n");
      for (XmlBuilder childElement : childElements) {
        resultCode.append(childElement.toCode());
      }
      resultCode.append(getZeroIndentation());
      resultCode.append("</");
      resultCode.append(tagName);
      resultCode.append(">");
    }
    resultCode.append("\r\n");
    return resultCode.toString();
  }

  public String getCleanTagName() {
    return WIDGET_NAME_PATTERN.matcher(tagName).replaceAll("");
  }

  private class AttributeBuilder {

    private final String value;
    private String namespace;
    private String attribute;

    private AttributeBuilder(String namespace, String attribute, String value) {
      this.namespace = namespace;
      this.attribute = attribute;
      this.value = value;
    }

    private AttributeBuilder(String value) {
      this.value = value;
    }

    private String toCode() {
      if (namespace != null && !namespace.isEmpty()) {
        return namespace + ":" + attribute + "=" + "\"" + value + "\"";
      } else if (attribute == null || attribute.isEmpty()) {
        return value.replaceAll("\n", lineBreak);
      } else {
        return attribute + "=" + "\"" + value + "\"";
      }
    }
  }
}
