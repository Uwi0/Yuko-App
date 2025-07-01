import SwiftUI
import Shared

struct HabitListView: View {
	
	let habits: [HabitModel]
	
	var body: some View {
		AdaptiveListGridView(items: habits) { habit in
				HabitItemView(habit: habit)
		}
	}
}

extension HabitModel: @retroactive Identifiable {
	public var id: Int64 { self.habitId }
}

#Preview {
	HabitListView(habits: [])
}
