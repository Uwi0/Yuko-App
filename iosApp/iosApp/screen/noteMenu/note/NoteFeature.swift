import Foundation
import ComposableArchitecture

@Reducer
struct NoteFeature {
	@ObservableState
	struct State: Equatable{
		let id: Int64
	}
	
	enum Action {
		case navigateBack
		case navigateToEdit(Int64)
	}
	
	var body: some ReducerOf<Self> {
		Reduce{ state, action in
			switch action {
			case .navigateBack: return .none
			case .navigateToEdit: return .none
			}
		}
	}
}
