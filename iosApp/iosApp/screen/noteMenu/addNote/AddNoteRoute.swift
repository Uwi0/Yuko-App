import SwiftUI
import ComposableArchitecture
import Shared

struct AddNoteRoute: View {
	
	@Bindable var store: StoreOf<AddNoteFeature>
	@StateObject private var viewModel = AddNoteViewModel()
	
	var body: some View {
		AddNoteScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData(noteId: store.state.id)
			}
			.onReceive(viewModel.effectPublisher) { effect in
				observe(effect: effect)
			}
	}
	
	private func observe(effect: AddNoteEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case .successSaveNote: store.send(.navigateBack)
		case let .showError(error): print("Error: \(error)")
		}
	}
}
