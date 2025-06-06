import Foundation
import Shared

extension DrawerMenuNavigation {
    func asIcon() -> String {
        let icon = switch self {
        case .pomodoro:"timer"
        case .todo:"checklist"
        case .habits:"flame"
        case .notes:"note.text"
        }
        return icon
    }
}

struct MenuWrapper: Identifiable, Hashable {
    let id: String
    let menu: DrawerMenuNavigation
    
    init(_ menu: DrawerMenuNavigation) {
        self.menu = menu
        self.id = menu.name
    }
}

let drawerMenus: [MenuWrapper] = [
    .init(.pomodoro),
    .init(.todo),
    .init(.notes),
    .init(.habits),
]
