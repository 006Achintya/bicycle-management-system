#!/bin/bash

# Path to the JAR file
JAR_FILE="target/College_Cycling-0.0.1-SNAPSHOT.jar"

# Base directory for templates in your project
TEMPLATE_DIR="src/main/resources/templates"

echo "🔍 Scanning all templates in $TEMPLATE_DIR..."
echo "-----------------------------------------------"

# Find all HTML files in templates directory
find "$TEMPLATE_DIR" -type f -name "*.html" | while read -r template; do
    # Get relative path inside JAR
    relative_path="BOOT-INF/classes/templates/${template#$TEMPLATE_DIR/}"
    
    # Check if template exists in JAR
    if unzip -l "$JAR_FILE" | grep -q "$relative_path"; then
        echo "✅ Found: $relative_path"
    else
        echo "❌ Missing: $relative_path"
    fi
done

echo "-----------------------------------------------"
echo "✔️  Scan completed!"
