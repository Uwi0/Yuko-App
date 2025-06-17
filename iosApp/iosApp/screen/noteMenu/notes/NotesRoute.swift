import SwiftUI
import Shared
import ComposableArchitecture

struct NotesRoute: View {
	
	@Bindable var store: StoreOf<NotesFeature>
	@StateObject private var viewModel = NotesViewModel()
	
	var body: some View {
		NotesScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublisher) { effect in
				observeEffect(effect: effect)
			}
	}
	
	private func observeEffect(effect: NotesEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case .tapToNote: store.send(.tapToNote)
		case .tapToAddNote: store.send(.tapToAddNote)
		case let .showError(error): print("error: \(error.message)")
		}
	}
}
