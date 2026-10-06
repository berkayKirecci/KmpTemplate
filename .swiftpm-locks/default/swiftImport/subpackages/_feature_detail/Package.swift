// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_feature_detail",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_feature_detail",
      type: .none,
      targets: ["_feature_detail"]
    )
  ],
  dependencies: [
  ],
  targets: [
    .target(
      name: "_feature_detail",
      dependencies: [
      ]
    )
  ]
)
