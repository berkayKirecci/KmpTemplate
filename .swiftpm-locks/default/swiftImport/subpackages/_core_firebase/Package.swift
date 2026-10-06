// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_core_firebase",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_core_firebase",
      type: .none,
      targets: ["_core_firebase"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/firebase/firebase-ios-sdk.git",
      from: "12.6.0"
    )
  ],
  targets: [
    .target(
      name: "_core_firebase",
      dependencies: [
        .product(
          name: "FirebaseCore",
          package: "firebase-ios-sdk"
        ),
        .product(
          name: "FirebaseAnalytics",
          package: "firebase-ios-sdk"
        ),
        .product(
          name: "FirebaseAuth",
          package: "firebase-ios-sdk"
        ),
        .product(
          name: "FirebaseFirestore",
          package: "firebase-ios-sdk"
        )
      ]
    )
  ]
)
