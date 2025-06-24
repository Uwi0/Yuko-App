import Foundation
import ComposableArchitecture

@Reducer
struct TodosFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateBack
		case tapToAddTodo
		case navigateToTodo(Int64)
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			case .tapToAddTodo: return .none
			case .navigateToTodo: return .none
			}
		}
	}
}
