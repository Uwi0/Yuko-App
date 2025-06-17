import SwiftUI
import Shared

struct NotesScreen: View {
	
	@Binding var state: NotesState
	let onEvent: (NotesEvent) -> Void
	
	var body: some View {
		VStack {
			TopbarView()
			Divider()
			ContentView()
			Spacer()
		}
	}
	
	@ViewBuilder
	private func TopbarView() -> some View {
		NavigationTopAppbar(
			title: "Notes",
			actionContent: {
				Image(systemName: "square.and.pencil")
					.onTapGesture { onEvent(.TapToAddNote()) }
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		NotesListView(notes: state.notes, onEvent: onEvent)
			.padding(.vertical, 24)
			.padding(.horizontal, 16)
	}
}

#Preview {
	NotesScreen(
		state: .constant(.companion.default()),
		onEvent: { _ in }
	)
}
