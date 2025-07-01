import SwiftUI

struct BorderedCardModifier: ViewModifier {
	@Environment(\.horizontalSizeClass) var horizontalSizeClass
	
	private var isGridView: Bool {
		horizontalSizeClass == .regular
	}
	func body(content: Content) -> some View {
		content
			.frame(
				maxWidth: isGridView ? SizeConstant.ItemMaxWidht : .infinity,
				alignment: .leading
			)
			.padding(.horizontal, 16)
			.padding(.vertical, 12)
			.background(ColorTheme.surface)
			.overlay(RoundedRectangle(cornerRadius: ShapeStyles.medium)
				.stroke(ColorTheme.onSurface, lineWidth: SizeConstant.BorderWidth))
	}
}
