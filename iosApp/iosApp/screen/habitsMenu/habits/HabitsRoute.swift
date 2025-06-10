import SwiftUI
import ComposableArchitecture

struct HabitsRoute: View {
	
	@Bindable var store: StoreOf<HabitsFeature>
	
	var body: some View {
		HabitsScreen()
	}
}

#Preview {
	HabitsRoute(
		store: Store(
			initialState: HabitsFeature.State(), reducer: { HabitsFeature() }
		)
	)
}
