import Foundation
import ComposableArchitecture

@Reducer
struct NotesFeature {
	@ObservableState
	struct State: Equatable, Identifiable {
		let id: UUID = UUID()
	}
	
	enum Action {
		case tapToAddNote
		case tapToNote
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .tapToAddNote: return .none
			case .tapToNote: return .none
			}
		}
	}
}
