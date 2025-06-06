import Foundation
import ComposableArchitecture

enum MainRoute {
    case pomodoro
    case note
    case todo
    case habit
    case settings
}

struct MainMenuFeature: Reducer {
    struct State: Equatable {
        var route: MainRoute? = nil
    }
    
    enum Action {
        case selectRoute(MainRoute)
        case dismissRoute
    }
    
    func reduce(into state: inout State, action: Action) -> Effect<Action> {
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
