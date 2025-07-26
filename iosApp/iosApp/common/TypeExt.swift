import Foundation

extension BinaryInteger {
		func removeThousandSeparator() -> String {
				String(format: "%d", Int64(self))
		}
}
