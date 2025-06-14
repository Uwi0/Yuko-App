import SwiftUI

struct OutlinedButtonView<Content: View>: View {
	let content: Content
	let onClick: () -> Void
	
	var body: some View {
		Button(action: onClick) {
			HStack(alignment: .center, spacing: 16) {
				content
					.font(Typography.titleMedium)
					.foregroundStyle(ColorTheme.primary)
					.frame(maxWidth: .infinity)
			}
			.padding(.vertical, 16)
			.padding(.horizontal, 16)
			.background(
				RoundedRectangle(cornerRadius: 16)
					.stroke(ColorTheme.primary, lineWidth: 2)
			)
			.contentShape(RoundedRectangle(cornerRadius: 16))
		}
		.buttonStyle(.plain)
	}
}

extension OutlinedButtonView {
	init(
		onClick: @escaping () -> Void,
		@ViewBuilder content: () -> Content
	) {
		self.content = content()
		self.onClick = onClick
	}
}

#Preview {
	OutlinedButtonView(onClick: {}, content: { Text("Hello world")})
}
