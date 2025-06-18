import SwiftUI
import Shared

struct NoteScreen: View {
	
	@Binding var state: NoteState
	let onEvent: (NoteEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbar()
			Divider()
			ContentView()
			Spacer()
		}
	}
	
	@ViewBuilder
	private func TopAppbar() -> some View {
		NavigationTopAppbar(
			title: "Note",
			actionContent: {
				Image(systemName: "pencil")
					.resizable()
					.aspectRatio(contentMode: .fit)
					.frame(width: 24, height: 24)
					.onTapGesture {
						onEvent(.EditNote())
					}
				Spacer().frame(width: 8)
				Image(systemName: "trash")
					.resizable()
					.aspectRatio(contentMode: .fit)
					.frame(width: 24, height: 24)
					.onTapGesture {
						onEvent(.DeleteNote())
					}
				
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			Text(state.note.title)
				.font(Typography.displayLarge)
			Text(state.note.note)
				.font(Typography.bodyLarge)
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
	
}

#Preview {
	NoteScreen(state: .constant(.companion.default()), onEvent: { _ in })
}
