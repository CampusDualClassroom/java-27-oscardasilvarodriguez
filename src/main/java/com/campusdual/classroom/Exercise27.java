package com.campusdual.classroom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Exercise27 {
    public static void main(String[] args) {

            createXMLFile();
            createJSONFile();
        }

    private static void createXMLFile() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            // Crear el documento y el elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("shoppinglist");
            doc.appendChild(rootElement);

            // Crear el elemento items
            Element items = doc.createElement("items");
            rootElement.appendChild(items);

            // Añadir los elementos de la lista
            addItem(doc, items, "Manzana", 2);
            addItem(doc, items, "Leche", 1);
            addItem(doc, items, "Pan", 3);
            addItem(doc, items, "Huevo", 2);
            addItem(doc, items, "Queso", 1);
            addItem(doc, items, "Cereal", 1);
            addItem(doc, items, "Agua", 4);
            addItem(doc, items, "Yogur", 6);
            addItem(doc, items, "Arroz", 2);

            // Asegurar que el directorio existe
            File directory = new File("src/main/resources");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Escribir el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/resources/shoppingList.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo XML creado con éxito");

        } catch (Exception e) {
            System.err.println("Error al crear el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addItem(Document doc, Element parent, String name, int quantity) {
        Element item = doc.createElement("item");
        item.setAttribute("quantity", String.valueOf(quantity));
        item.setTextContent(name);
        parent.appendChild(item);
    }

    private static void createJSONFile() {
        JsonObject root = new JsonObject();
        JsonArray items = new JsonArray();

        items.add(createJsonItem("Manzana", 2));
        items.add(createJsonItem("Leche", 1));
        items.add(createJsonItem("Pan", 3));
        items.add(createJsonItem("Huevo", 2));
        items.add(createJsonItem("Queso", 1));
        items.add(createJsonItem("Cereal", 1));
        items.add(createJsonItem("Agua", 4));
        items.add(createJsonItem("Yogur", 6));
        items.add(createJsonItem("Arroz", 2));

        root.add("items", items);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(root);

        // Asegurar que el directorio existe
        File directory = new File("src/main/resources");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter file = new FileWriter("src/main/resources/shoppingList.json")) {
            file.write(jsonString);
            System.out.println("Archivo JSON creado con éxito");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JsonObject createJsonItem(String name, int quantity) {
        JsonObject item = new JsonObject();
        item.addProperty("text", name);
        item.addProperty("quantity", quantity);
        return item;
    }
}

