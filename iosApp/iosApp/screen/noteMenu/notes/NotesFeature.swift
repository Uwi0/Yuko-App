import Foundation
import ComposableArchitecture

@Reducer
struct NotesFeature {
	@ObservableState
	struct State: Equatable, Identifiable {
		let id: UUID = UUID()
	}
	
	enum Action {
		case navigateBack
		case tapToAddNote
		case tapToNote(Int64)
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .tapToAddNote: return .none
			case .tapToNote: return .none
			case .navigateBack: return .none
			}
		}
	}
}
