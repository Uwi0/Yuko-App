import Foundation
import ComposableArchitecture

@Reducer
struct GoodHabitFeature {
	@ObservableState
	struct State: Equatable {
		var id: Int64 = 0
	}
	
	enum Action {
		
	}
	
	var body: some ReducerOf<Self> {
		
	}
}
