import SwiftUI
import ComposableArchitecture

struct GoodHabitRoute: View {
	
	@Bindable var store: StoreOf<GoodHabitFeature>
	
	var body: some View {
		GoodHabitScreen()
	}
}
