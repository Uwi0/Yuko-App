import SwiftUI
import Shared

struct AddNoteScreen: View {
	
	let onEvent: (AddNoteEvent) -> Void
	
	var body: some View {
		VStack() {
			TopBar()
			Divider()
			Content()
			Spacer()
		}
	}
	
	@ViewBuilder
	private func TopBar() -> some View {
		NavigationTopAppbar(
			title: "Add Note",
			onAction: { onEvent(.NavigateBack())}
		)
	}

	@ViewBuilder
	private func Content() -> some View {
		VStack {
			Text("Hello Add note")
		}
		.padding(.vertical, 24)
		.padding(.horizontal, 16)
	}
}

#Preview {
	AddNoteScreen(onEvent: { _ in })
}
