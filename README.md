 # 🎬 Movies - Aplicación Android
 
 Movies es una aplicación de Android que permite a los usuarios explorar películas populares, rastrear ubicaciones y subir imágenes.  
 La app utiliza **TMDb API**, **Google Maps**, **Firebase Firestore** y **Firebase Storage** para ofrecer una experiencia completa.
 
 ---
 
 ## 🚀 **Características principales**
 ✅ **Explorar películas populares y recomendadas** con la API de **TMDb**  
 ✅ **Guardar la ubicación del usuario** en Firestore cada 5 minutos  
 ✅ **Mostrar un historial de ubicaciones en una lista**  
 ✅ **Visualizar ubicaciones en Google Maps** con detalles  
 ✅ **Subir imágenes a Firebase Storage** desde la galería  
 ✅ **Notificaciones en Android 13+** para actualizaciones de ubicación
 
 ---
 
 ## 📲 **Configuración del Proyecto**
 ### 🔹 **1. Clonar el Repositorio**
 ```bash
 git clone https://github.com/arturog278/android-movies-app.git
 cd movies
 ```
 
 ### 🔹 **2. Configurar API Keys**
 📌 **Edita `local.properties` y añade las claves necesarias:**
 ```
 TMDB_API_KEY=tu_tmdb_api_key
 MAPS_API_KEY=tu_maps_api_key
 ```
 📌 **Estas claves se inyectan automáticamente en `BuildConfig`.**

 ### 🔹 **3. Configurar Firebase (`google-services.json`)**
 Para utilizar Firebase Firestore y Firebase Storage, es necesario configurar `google-services.json`:

 1. **Ir a la [Consola de Firebase](https://console.firebase.google.com/).**
 2. **Seleccionar tu proyecto y agregar una nueva aplicación Android.**
 3. **Descargar el archivo `google-services.json`.**
 4. **Colocar el archivo dentro de la carpeta `app/` del proyecto.**

 📌 **`google-services.json` contiene información sensible y NO debe subirse a GitHub.**  
 Para evitar que se suba, asegúrate de que el archivo `app/google-services.json` está en **`.gitignore`**.

 ### 🔹 **4. Sincronizar Gradle y Ejecutar la App**
 - Abre el proyecto en **Android Studio**
 - **Sincroniza Gradle** (`Sync Now`)
 - Ejecuta la aplicación en un dispositivo/emulador
 
 ---
 
 ## 🛠 **Tecnologías Utilizadas**
 - **Kotlin** - Lenguaje principal
 - **MVVM** - Arquitectura
 - **Hilt** - Inyección de dependencias
 - **Retrofit** - Consumo de API TMDb
 - **Firebase Firestore** - Base de datos en la nube
 - **Google Maps API** - Integración con mapas
 - **Glide** - Carga de imágenes
 
 ---
 
 ## 📌 **Estructura del Proyecto**
 ```
 📂 Movies
  ┣ 📂 app
  ┃ ┣ 📂 src/main
  ┃ ┃ ┣ 📂 java/com/arturo/movies
  ┃ ┃ ┃ ┣ 📂 ui             # Fragments y Activities
  ┃ ┃ ┃ ┣ 📂 viewmodel      # ViewModels con LiveData
  ┃ ┃ ┃ ┣ 📂 data           # Repositorios y lógica de datos
  ┃ ┃ ┃ ┣ 📂 di             # Inyección de dependencias con Hilt
  ┃ ┃ ┃ ┣ 📂 model          # Modelos de datos (Movie, LocationData)
  ┃ ┃ ┣ 📂 res              # Recursos (layouts, drawables, mipmaps)
 ```
 
 ---
 
 ## 📄 **Licencia**
 Este proyecto está bajo la licencia MIT. Puedes utilizarlo y modificarlo libremente.
 
 ---
 
 🎬 **Desarrollado por Arturo** 🚀  
 Si tienes preguntas o mejoras, ¡envía un PR o abre un issue en GitHub!