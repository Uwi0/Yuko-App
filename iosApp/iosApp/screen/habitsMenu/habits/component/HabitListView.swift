import SwiftUI
import Shared

struct HabitListView: View {
	
	let habits: [HabitItemModel]
	let onEvent: (HabitsEvent) -> Void
	
	var body: some View {
		AdaptiveListGridView(items: habits) { habit in
				HabitItemView(habit: habit, onEvent: onEvent)
		}
	}
}


#Preview {
	HabitListView(habits: [], onEvent: { _ in })
}
