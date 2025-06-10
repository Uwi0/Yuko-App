import SwiftUI
import ComposableArchitecture

struct SettingsRoute: View {
	
	@Bindable var store: StoreOf<SettingsFeature>
	
    var body: some View {
        SettingsScreen()
    }
}

#Preview {
    SettingsRoute(
			store: Store(
				initialState: SettingsFeature.State(),
				reducer: { SettingsFeature()}
			)
		)
}
