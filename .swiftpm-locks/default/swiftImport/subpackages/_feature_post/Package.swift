// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_feature_post",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_feature_post",
      type: .none,
      targets: ["_feature_post"]
    )
  ],
  dependencies: [
  ],
  targets: [
    .target(
      name: "_feature_post",
      dependencies: [
      ]
    )
  ]
)
