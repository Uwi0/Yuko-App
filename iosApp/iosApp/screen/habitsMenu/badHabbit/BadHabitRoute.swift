import SwiftUI
import ComposableArchitecture

struct BadHabitRoute: View {
	
	@Bindable var store: StoreOf<BadHabitFeature>
	
	var body: some View {
		BadHabitScreen()
	}
}
