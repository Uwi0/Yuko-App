import Foundation
import ComposableArchitecture

extension RootFeature {
	static func reduceNoteNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToNotes):
			state.path = StackState()
			state.path.append(.notesScreen(NotesFeature.State()))
			return .none
			
		case .path(.element(_, .notesScreen(.tapToAddNote))):
			state.path.append(.addNoteScreen(AddNoteFeature.State()))
			return .none
			
		case .path(.element(_, .notesScreen(.tapToNote(let id)))):
			state.path.append(.noteScreen(NoteFeature.State(id: id)))
			return .none
			
		case .path(.element(_, .notesScreen(.navigateBack))):
			return .run { send in await send(.navigateBack)}
			
		case .path(.element(_, .addNoteScreen(.navigateBack))):
			return .run { send in await send(.navigateBack)}
			
		case .path(.element(_, .noteScreen(.navigateBack))):
			return .run { send in await send(.navigateBack)}
			
		case .path(.element(_, .noteScreen(.navigateToEdit(let noteId)))):
			state.path.append(.addNoteScreen(AddNoteFeature.State(id: noteId)))
			return .none
			
		default: return .none
		}
	}
}
