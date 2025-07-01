import SwiftUI
import Shared

struct HabitListView: View {
	
	let habits: [HabitModel]
	let onEvent: (HabitsEvent) -> Void
	
	var body: some View {
		AdaptiveListGridView(items: habits) { habit in
				HabitItemView(habit: habit, onEvent: onEvent)
		}
	}
}

extension HabitModel: @retroactive Identifiable {
	public var id: Int64 { self.habitId }
}

#Preview {
	HabitListView(habits: [], onEvent: { _ in })
}
