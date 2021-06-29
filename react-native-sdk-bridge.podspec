# react-native-sdk-bridge.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-sdk-bridge"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-sdk-bridge
                   DESC
  s.homepage     = "https://github.com/mises-id/react-native-sdk-bridge"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Your Name" => "baoge_pmb@hotmail.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.commises-id/react-native-sdk-bridge.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  s.requires_arc = true

  s.dependency "React"
  # ...
  # s.dependency "..."
end

