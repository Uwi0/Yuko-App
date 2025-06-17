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
	}
	
	var body: some ReducerOf<Self> {
		Reduce{ state, action in
			switch action {
			case .navigateBack: return .none
			}
		}
	}
}
