import SwiftUI
import Shared

struct TodoScreen: View {
	
	let onEvent: (TodoEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbarView()
			Divider()
			ContentView()
		}
	}
	
	@ViewBuilder
	private func TopAppbarView() -> some View {
		NavigationTopAppbar(title: "Todo", onAction: { onEvent(.NavigateBack())})
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	TodoScreen(onEvent: { _ in })
}
