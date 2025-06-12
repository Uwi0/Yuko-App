import SwiftUI
import Shared

struct NotesScreen: View {
	
	let onEvent: (NotesEvent) -> Void
	
	var body: some View {
		VStack(spacing: 16) {
			Text("Hello Notes")
			HStack {
				Button("Go to Note"){
					onEvent(.TapToNote())
				}
				Button("Go to Add Note") {
					onEvent(.TapToAddNote())
				}
			}
		}
		
	}
}

#Preview {
	NotesScreen(onEvent: { _ in })
}
