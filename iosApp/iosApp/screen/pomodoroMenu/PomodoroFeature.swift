import Foundation
import ComposableArchitecture

enum PomodoroRoute: Equatable {
    case success
    case fail
}

struct PomodoroFeature: Reducer {
    struct State: Equatable {
        var route: PomodoroRoute? = nil
        
        var isPresented: Bool {
            route == .success || route == .fail
        }
    }
    
    enum Action: Equatable {
        case timerCompleted
        case timerFailed
        case routeDismissed
        case tapBreak
        case tabBackToMainMenu
    }
    
    func reduce(into state: inout State, action: Action) -> Effect<Action> {
        switch action {
        case .timerCompleted:
            state.route = .success
            return .none
        case .timerFailed:
            state.route = .fail
            return .none
        case .routeDismissed:
            state.route = nil
            return .none
        case .tapBreak:
            state.route = nil
            return .none
        case .tabBackToMainMenu:
            return .none
        }
    }
}
