// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "KotlinMultiplatformLinkedPackage",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "KotlinMultiplatformLinkedPackage",
      type: .none,
      targets: ["KotlinMultiplatformLinkedPackage"]
    )
  ],
  dependencies: [
    .package(path: "subpackages/_core_ads"),
    .package(path: "subpackages/_core_firebase"),
    .package(path: "subpackages/_feature_post"),
    .package(path: "subpackages/_shared")
  ],
  targets: [
    .target(
      name: "KotlinMultiplatformLinkedPackage",
      dependencies: [
        .product(name: "_core_ads", package: "_core_ads"),
        .product(name: "_core_firebase", package: "_core_firebase"),
        .product(name: "_feature_post", package: "_feature_post"),
        .product(name: "_shared", package: "_shared")
      ]
    )
  ]
)
