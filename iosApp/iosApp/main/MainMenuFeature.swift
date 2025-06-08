import Foundation
import ComposableArchitecture

enum MainRoute {
    case pomodoro
    case pomodoroSuccess
    case pomodoroFail
    case note
    case todo
    case habit
    case settings
}

@Reducer
struct MainMenuFeature: Reducer {
    @ObservableState
    struct State: Equatable {
        var route: MainRoute? = nil
    }
    
    enum Action {
        case selectRoute(MainRoute)
        case dismissRoute
    }
    
    var body: some ReducerOf<Self> {
        Reduce { state, action in
            switch action {
            case .selectRoute(let destination):
                state.route = destination
                return .none
            case .dismissRoute:
                state.route = nil
                return .none
            }
        }
    }
    
}
