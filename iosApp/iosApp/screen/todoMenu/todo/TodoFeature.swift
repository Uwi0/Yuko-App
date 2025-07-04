import Foundation
import ComposableArchitecture

@Reducer
struct TodoFeature {
	@ObservableState
	struct State: Equatable {
		var todoId: Int64 = 0
	}
	
	enum Action {
		case navigateBack
		case tapToEditTodo(Int64)
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			case .tapToEditTodo: return .none
			}
		}
	}
}
