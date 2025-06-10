import SwiftUI
import ComposableArchitecture

struct MainMenuScreen: View {
	let store: StoreOf<MainMenuFeature>
	
	var body: some View {
		HStack(spacing: 16) {
			Button("Pomodoro") {
				store.send(.tapToPomodoro)
			}
			
			Button("Notes") {
				store.send(.tapToNotes)
			}
			
			Button("Todos") {
				store.send(.tapToTodos)
			}
			
			Button("Habits") {
				store.send(.tapToHabits)
			}
			
			Button("Settings") {
				store.send(.tapToSettings)
			}
		}
	}
}
