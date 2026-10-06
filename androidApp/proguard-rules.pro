# R8/ProGuard rules for the release build.
# Referenced from AndroidApplicationConventionPlugin alongside proguard-android-optimize.txt.

# Kotlin reflection metadata used by kotlinx.serialization and Koin.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault,InnerClasses,Signature

# kotlinx.serialization: keep generated serializers for @Serializable types.
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers class ** {
    public static *** Companion;
}
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Ktor engines are resolved via ServiceLoader.
-keep class io.ktor.client.engine.** { *; }
-dontwarn io.ktor.**
-dontwarn org.slf4j.**
