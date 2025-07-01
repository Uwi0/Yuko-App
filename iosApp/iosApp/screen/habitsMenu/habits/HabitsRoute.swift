import SwiftUI
import Shared
import ComposableArchitecture

struct HabitsRoute: View {
	
	@Bindable var store: StoreOf<HabitsFeature>
	@StateObject private var viewModel = HabitsViewModel()
	
	var body: some View {
		HabitsScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublisher) { effect in
					observe(effect: effect)
			}
	}
	
	private func observe(effect: HabitsEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case .tapToAddHabit: store.send(.tapToAddHabit)
		case let .showError(error): print("error: \(error)")
		}
	}
}

