package org.crishof.imagesv.util;

public class CloudinaryUtils {

    public static String extractPublicIdFromUrl(String imageUrl) {
        // Buscar el índice del segmento de la URL que sigue a "/upload/"
        int startIndex = imageUrl.indexOf("/CATEGORY/") + "/CATEGORY/".length();

        System.out.println("startIndex = " + startIndex);

        // Buscar el índice del próximo "/" después del startIndex
        int endIndex = imageUrl.length();

        System.out.println("endIndex = " + endIndex);

        // Buscar el índice del último "/" antes de "/CATEGORY/"
        int lastIndexBeforeCategory = imageUrl.lastIndexOf("/", imageUrl.indexOf("/CATEGORY/"));

        System.out.println("lastIndexBeforeCategory = " + lastIndexBeforeCategory);

        if (endIndex != -1 && lastIndexBeforeCategory != -1) {
            // Extraer el segmento de la URL que contiene el publicId
            System.out.println(imageUrl.substring(startIndex, lastIndexBeforeCategory));
            return imageUrl.substring(60);


        } else {
            // La URL no sigue el formato esperado
            throw new IllegalArgumentException("Invalid Cloudinary URL");
        }
    }
//   http://res.cloudinary.com/dm7r09stb/image /upload/v1710951270 /CATEGORY/bdowml8vqahqm5yvid0r.png
//   http://res.cloudinary.com/dm7r09stb/image/upload/v1710950088/CATEGORY/i9mvtegjalrtziydswqj.png

}
