import SwiftUI
import Shared

struct AddNoteScreen: View {
	
	@Binding var state: AddNoteState
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
			TextField(
				"Title",
				text: Binding(
					get: { state.title},
					set: { title in onEvent(.TitleChanged(title: title))})
			)
			.font(Typography.titleLarge)
			
			TextField(
				"Note",
				text: Binding(
					get: { state.note },
					set: { note in onEvent(.NoteChanged(note: note))}
				)
			)
			.font(Typography.bodyMedium)
			
		}
		.padding(.vertical, 24)
		.padding(.horizontal, 16)
	}
}

#Preview {
	
	AddNoteScreen(
		state: .constant(.companion.default())
		,onEvent: { _ in }
	)
}
