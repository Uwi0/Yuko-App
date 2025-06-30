import SwiftUI
import Shared

struct AddHabitScren: View {
	
	@Binding var state: AddHabitState
	let onEvent: (AddHabitEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbarView()
			Divider()
			ContentView()
			
		}
	}
	
	@ViewBuilder
	private func TopAppbarView() -> some View {
		NavigationTopAppbar(
			title: "Add Habit",
			actionContent: {
				SaveIconButton()
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func SaveIconButton() -> some View {
		Image(systemName: "square.and.arrow.down")
			.resizable()
			.scaledToFit()
			.frame(width: 24, height: 24)
			.onTapGesture {
				onEvent(.SaveHabit())
			}
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			TextField(
				"Name",
				text: Binding(
					get: { state.name },
					set: { value in onEvent(.NameChanged(name: value))}
				)
			)
			.font(Typography.titleLarge)
			TextField(
				"Description",
				text: Binding(
					get: { state.description_ },
					set: { value in onEvent(.DescriptionChanged(description: value))}
				)
			)
			.font(Typography.bodyMedium)
			Toggle(
				"Is Good Habit ?",
				isOn: Binding(
					get: { state.isToggleOn },
					set: { _ in onEvent(.ToggleType()) }
				)
			)
//			ReminderHabitView(state: $state, onEvent: onEvent)
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	AddHabitScren(state: .constant(.companion.default()),onEvent: { _ in })
}
