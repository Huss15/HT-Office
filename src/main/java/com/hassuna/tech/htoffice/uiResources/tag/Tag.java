package com.hassuna.tech.htoffice.uiResources.tag;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public int tagIdentifier;
    public String tagType;
    public String severity;
    public String displayString;
    public String iconName;

    public Tag() {
    }

    public Tag(int tagIdentifier, String tagType, String severity, String displayString, String iconName) {
        this.tagIdentifier = tagIdentifier;
        this.tagType = tagType;
        this.severity = severity;
        this.displayString = displayString;
        this.iconName = iconName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTagIdentifier() {
        return tagIdentifier;
    }

    public void setTagIdentifier(int tagIdentifier) {
        this.tagIdentifier = tagIdentifier;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
