package com.aues.reservationms.controller;


import java.io.FileOutputStream;
import java.io.IOException;

public class lab51 {

    public static void main(String[] args) {
        // URL страницы, которую мы собираемся парсить
        String url = "https://www.sulpak.kz/f/led_oled_televizoriy";

        try {
            // Получаем HTML-код страницы
            Document doc = Jsoup.connect(url).get();

            // Создаем новую книгу Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Телевизоры");

            // Заголовки для Excel файла
            String[] headers = {"Наименование", "Бренд", "Артикул", "Цена", "Ссылка на изображение"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Ищем необходимые элементы на странице
            Elements products = doc.select("div.tile-container"); // Используйте правильный CSS-селектор для вашего элемента
            int rowNum = 1;
            for (Element product : products) {
                // Извлекаем информацию о каждом продукте
                String name = product.select("a.title").text(); // Используйте правильный CSS-селектор
                String brand = product.select("div.brand").text(); // Используйте правильный CSS-селектор
                String article = product.select("span.article").text(); // Используйте правильный CSS-селектор
                String price = product.select("div.price").text(); // Используйте правильный CSS-селектор
                String imageUrl = product.select("img").attr("src"); // Используйте правильный CSS-селектор

                // Создаем новую строку в Excel файле
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(brand);
                row.createCell(2).setCellValue(article);
                row.createCell(3).setCellValue(price);
                row.createCell(4).setCellValue(imageUrl);
            }

            // Записываем Excel файл
            try (FileOutputStream outputStream = new FileOutputStream("Телевизоры.xlsx")) {
                workbook.write(outputStream);
            }

            // Закрываем книгу
            workbook.close();

            System.out.println("Данные успешно сохранены в файл 'Телевизоры.xlsx'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
