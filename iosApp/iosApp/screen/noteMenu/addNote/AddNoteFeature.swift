import Foundation
import ComposableArchitecture

@Reducer
struct AddNoteFeature {
	@ObservableState
	struct State: Equatable, Identifiable {
		let id: UUID = UUID()
	}
	
	enum Action {
		case navigateBack
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			}
		}
	}
}
