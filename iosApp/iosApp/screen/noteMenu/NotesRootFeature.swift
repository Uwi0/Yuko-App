import Foundation
import ComposableArchitecture

@Reducer
struct NotesRootFeature {
	@ObservableState
	struct State: Equatable {
		var path = StackState<Path.State>()
		var notes = NotesFeature.State()
	}
	
	enum Action {
		case path(StackAction<Path.State, Path.Action>)
		case notes(NotesFeature.Action)
	}
	
	@Reducer(state: .equatable)
	enum Path {
		case addNoteScreen(AddNoteFeature)
		case noteScreen(NoteFeature)
	}
	
	var body: some ReducerOf<Self> {
		Scope(state: \.notes, action: \.notes) {
			NotesFeature()
		}
		
		Reduce { state, action in
			switch action {
			case .path: return .none
			case .notes: return .none
			}
		}
		.forEach(\.path, action: \.path)
	}
}
