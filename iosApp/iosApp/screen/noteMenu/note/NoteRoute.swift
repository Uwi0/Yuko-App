import SwiftUI
import ComposableArchitecture
import Shared

struct NoteRoute: View {
	
	@Bindable var store: StoreOf<NoteFeature>
	@StateObject private var viewModel = NoteViewModel()
	
	var body: some View {
		NoteScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData(noteId: store.state.id)
			}
			.onReceive(viewModel.effectPublisher){ effect in
				observeEffect(effect: effect)
			}
	}
	
	private func observeEffect(effect: NoteEffect) {
		switch onEnum(of: effect) {
		case let .showError(error): print(error.message)
		case .navigateBack: store.send(.navigateBack)
		case let .tapToEditNote(effect): store.send(.navigateToEdit(effect.noteId))
		}
	}
}
