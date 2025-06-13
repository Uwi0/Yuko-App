import SwiftUI

struct FilledButtonView<Content: View>: View {
	let content: Content
	let enabled: Bool
	let onclick: () -> Void
	
	private var containerColor: Color {
		enabled ? ColorTheme.primary : ColorTheme.onSurface
	}
	
	private var contentColor: Color {
		enabled ? ColorTheme.onPrimary : ColorTheme.onSurface.opacity(0.38)
	}
	
	var body: some View {
		Button(action: onclick) {
			HStack(spacing: 16) {
				content
					.font(Typography.titleMedium)
					.foregroundStyle(contentColor)
					.frame(maxWidth: .infinity)
			}
			.padding(.vertical, 16)
			.padding(.horizontal, 16)
			.background(
				RoundedRectangle(cornerRadius: 16)
					.fill(containerColor)
					.opacity(enabled ? 1 : 0.12)
			)
			.contentShape(RoundedRectangle(cornerRadius: 16))
		}
		.disabled(!enabled)
		.buttonStyle(.plain)
		.animation(.default, value: enabled)
	}
}

extension FilledButtonView {
	init(
		onClick: @escaping () -> Void,
		enabled: Bool = true,
		@ViewBuilder content: () -> Content
	) {
		self.content = content()
		self.onclick = onClick
		self.enabled = enabled
	}
}
#Preview {
	VStack {
		FilledButtonView(
			onClick: {},
			content: { Text("Hello") }
		)
		FilledButtonView(
			onClick: {},
			enabled: false,
			content: { Text("Hello") }
		)
	}
}
